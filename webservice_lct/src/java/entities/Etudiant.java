package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "ETUDIANT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etudiant.findAll", query = "SELECT e FROM Etudiant e"),
    @NamedQuery(name = "Etudiant.findById", query = "SELECT e FROM Etudiant e WHERE e.id = :id"),
    @NamedQuery(name = "Etudiant.findByNom", query = "SELECT e FROM Etudiant e WHERE e.nom = :nom"),
    @NamedQuery(name = "Etudiant.findByPrenom", query = "SELECT e FROM Etudiant e WHERE e.prenom = :prenom"),
    @NamedQuery(name = "Etudiant.findByEmail", query = "SELECT e FROM Etudiant e WHERE e.email = :email"),
    @NamedQuery(name = "Etudiant.findByLoginAndPw", query = "SELECT e FROM Etudiant e WHERE e.email = :login AND e.pw = :pw"),
    @NamedQuery(name = "Etudiant.findByBudget", query = "SELECT e FROM Etudiant e WHERE e.budget = :budget")})
public class Etudiant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableGenerator(name="ETU_GEN", table="SEQUENCE", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", pkColumnValue="ETU_SEQ")
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ETU_GEN")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOM")
    private String nom;
    
    @Column(name = "PRENOM")
    private String prenom;
   
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PW")
    private String pw;
    
    @Column(name = "BUDGET")
    private int budget;

    public Etudiant() {
    }

    public Etudiant(String nom, String prenom, String email, String pw, int budget) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.pw = pw;
        this.budget = budget;
    }

    @XmlElement(name="id")
    public Long getId() {
        return id;
    }
    
    @XmlElement(name="nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlElement(name="prenom")
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @XmlElement(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    @XmlElement(name="budget")
    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Etudiant other = (Etudiant) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Etudiant{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", budget=" + budget + '}';
    }

    
    
}
