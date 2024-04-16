package edu.epam.fop.lambdas;

import edu.epam.fop.lambdas.Token.Type;
import java.util.Set;

public final class TextStatistics {

  private TextStatistics() {
    throw new UnsupportedOperationException();
  }

  // Method to count occurrences of tokens in the sequence
  public static TokenStatisticsCalculator<Token, Long> countTokens() {
    return (map, tokens) ->{
      while(tokens.hasNext()){
        // Increment token count in the map
        map.merge(tokens.next(), 1L, (oldCount, newValue) -> oldCount + newValue);
      }
    };
  }

  // Method to count occurrences of known tokens within a limit
  public static TokenStatisticsCalculator<Token, Long> countKnownTokensWithMaxLimit(int maxLimit) {
    return (map, tokens) ->{
      while(tokens.hasNext()){
        // Increment token count only if within the limit
        map.merge(tokens.next(), 1L, (oldCount, newValue) -> oldCount < maxLimit ? oldCount + newValue : oldCount);
      }
    };
  }

  // Method to mark unknown tokens of specific types
  public static TokenStatisticsCalculator<Token, Boolean> findUnknownTokensOfTypes(Set<Type> types) {
    return (map, tokens) -> {
      while(tokens.hasNext()){
        Token currentToken = tokens.next();
        // Check if the token's type is in the specified set
        if(types.contains(currentToken.type()))
          // Add the token to the map with a value of true if its type is found
          map.computeIfAbsent(currentToken, (token) -> types.contains(token.type()));
      }
    };
  }

  // Method to define specific codes for tokens based on various conditions
  public static TokenStatisticsCalculator<Token, Integer> combinedSearch(int maxLimit, Set<Type> types) {
    return (map, tokens) -> {
      while(tokens.hasNext()){
        Token currentToken = tokens.next();
        map.merge(currentToken, -1,(oldValue, newValue) -> {
          // Token exists in the map and its type is in the specified set
          if(map.containsKey(currentToken) && types.contains(currentToken.type()))
            return oldValue <= maxLimit ? 0 : 1; // Stored with value 0 or 1 based on the limit
            // Token exists in the map but its type is not in the specified set
          else if (map.containsKey(currentToken)) {
            return oldValue <= maxLimit ? 2 : 3; // Stored with value 2 or 3 based on the limit
          }
          // Token doesn't exist in the map
          return -1; // Stored with value -1
        });
      }
    };
  }

}

