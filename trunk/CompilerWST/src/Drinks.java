import java.util.ArrayList;
import java.util.List;

public class Drinks {
	String drinkid;
	
	//List of ingredients in a drink
	List<Ingredients> ListOfIngredient;
	
	public int getIngredientcount(){
		return ListOfIngredient.size();
	}
	
	//Constructer for Drinks class
	Drinks(String ID){
		drinkid = ID;
		ListOfIngredient = new ArrayList<Ingredients>();
	}
}
