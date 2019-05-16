package mk.trkalo.emtlab.EMTlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;

    @ManyToOne
    @JsonIgnoreProperties("branch")
    public User manager;

    private Branch(){}
    public Branch(String name){
        this.name=name;
    }
    public Branch(String name, User manager){
        this.manager = manager;
        this.name = name;
    }
}
