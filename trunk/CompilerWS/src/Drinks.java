import java.util.ArrayList;
import java.util.List;

public class Drinks {
	String drinkid;
	List<Ingredients> ListOfIngredient;
	
	public int getIngredientcount(){
		return ListOfIngredient.size();
	}
	
	Drinks(String ID){
		drinkid = ID;
		ListOfIngredient = new ArrayList<Ingredients>();
	}
}
