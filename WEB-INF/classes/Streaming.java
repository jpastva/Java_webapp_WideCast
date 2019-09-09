import java.time.LocalDate;

public interface Streaming {
	
	public String getId();

	public void setId(String id);
	
	public String getName();

	public void setName(String name);

	public double getPrice();

	public void setPrice(double price);

	public String getImage();

	public void setImage(String image);

	public String getDistributor();

	public void setDistributor(String distributor);

	public void setGenre(String genre);

	public String getGenre();

	public int getRuntime();

	public void setRuntime(int runtime);

	public LocalDate getDate();

	public void setDate(String date);
}