package uni.aed.tda.treeTDA.SoftwareStore;

public class Software implements Comparable<Software>{
    String name;
    String version;
    int quantity;
    int price;
    int filePosition;

    public Software(String name, String version, int quantity, int price) {
        this.name = name;
        this.version = version;
        this.quantity = quantity;
        this.price = price;
    }
    public Software(String name, String version, int quantity, int price,int filePosition) {
        this.name = name;
        this.version = version;
        this.quantity = quantity;
        this.price = price;
        this.filePosition=filePosition;
    }
    public Software(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getFilePosition() {
        return filePosition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setFilePosition(int filePosition) {
        this.filePosition = filePosition;
    }

    @Override
    public String toString() {
        return name + " " + version + " " + quantity + " " + price;
    }

    @Override
    public int compareTo(Software o) {        
        return (this.name+this.version).compareTo(o.getName()+o.getVersion());
    }    

}
