package model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private List<Seller> sellerList = new ArrayList<>();

    public Department() {}

    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addSeller(Seller seller) {
        this.sellerList.add(seller);
    }

    public List<Seller> getSellerList() {
        return sellerList;
    }

    public StringBuilder formattedSellers() {
        StringBuilder selers = new StringBuilder(100);

        for (Seller seller : this.sellerList) {
            selers.append(seller.toString());
        }

        return selers;
    }

    public String toString() {
        return this.id + ", " + this.name + " " + this.formattedSellers();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
