public class Sheep {

   enum Animal {sheep, goat};



   public static void main (String[] param) {
      // for debugging

   }

   public static void reorder (Animal[] animals) {
      // TODO!!! Your program here
      int k =  0;
      int l = 0;

      for (int i=0; i<animals.length; i++){
         if (animals[i].equals(Animal.goat)){
            k++;
         }
      }

      for (int i=0; i<animals.length; i++){
         if (animals[i].equals(Animal.sheep)){
            l++;
         }
      }
      for (int i = 0; i<k;i++){
         animals[i] = Animal.goat;
      }
      for (int i = k; i<k+l; i++){
         animals[i] = Animal.sheep;

      }


   }
}