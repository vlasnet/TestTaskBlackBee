package vlasyuk.testtaskblackbee;

/**
 *
 * @author Sergey
 */
public class Product {

    public Product() {
    }
    private String brand;
    private String name;
    private String color;
    private String price;
    private String initialPrice;
    private String description;
    private String ArticleID;    

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArticleID() {
        return ArticleID;
    }

    public void setArticleID(String ArticleID) {
        this.ArticleID = ArticleID;
    }

    @Override
    public String toString() {
        return "Product{" + "brand=" + brand + ",\n name=" + name + ",\n color=" + color + ",\n price=" + price + ",\n initialPrice=" + initialPrice + ",\n description=" + description + "ArticleID=" + ArticleID + '}';
    }
}
