import java.io.*;
import java.util.*;

class BusinessInfo implements Comparable<BusinessInfo> {
  int id;
  double rating;

  public BusinessInfo(int id, double rating) {
    this.id = id;
    this.rating = rating;
  }

  public int compareTo(BusinessInfo b1) {
      if (this.rating < b1.rating) {
          return -1;
      }
      if (this.rating > b1.rating) {
          return 1;
      }
      return 0;
  }
}

class Solution {

  /**    
   * List of business info data where each element is a BusinessInfo object 
   * containing id and rating. Determine the median rating across all 
   * businesses in businessInfoList.
   *
   * @param businessInfoList List of business info objects where BusinessInfo 
   *     is a class containing id and rating.
   *     
   * @return Median rating of all businesses
   */
  public static double calculateMedianRating(List<BusinessInfo> businessInfoList) {
      // Sort the list according to its rating by the given `compareTo`
      Collections.sort(businessInfoList, new Comparator<BusinessInfo>(){
          @Override
          public int compare(BusinessInfo business1, BusinessInfo business2){
              return business1.compareTo(business2);
          }
      });
      int n = businessInfoList.size();
      // Return midpoint if is odd; else return average of two at middle
      return ((n & 1) != 0) ? businessInfoList.get(n / 2).rating:
          (businessInfoList.get(n / 2 - 1).rating + businessInfoList.get(n / 2).rating) / 2.0;
  }

  public static void main(String[] args) {
    String line;
    List<BusinessInfo> businessInfoList = new ArrayList<>();
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        businessInfoList.add(
          new BusinessInfo(
            Integer.parseInt(parts[0]),
            Double.parseDouble(parts[1])
          )
        );
      }
      
      double medianRating = calculateMedianRating(businessInfoList);
      System.out.println(medianRating);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}  
