package com.tornabene.covid19app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.tornabene.covid19app.domain.enumeration.Sex;

/**
 * A Paziente.
 */
@Entity
@Table(name = "paziente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Paziente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cf")
    private String cf;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "comune")
    private String comune;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "regione")
    private String regione;

    @Column(name = "latitudine")
    private String latitudine;

    @Column(name = "longitudine")
    private String longitudine;

    @Column(name = "numero_famigliari")
    private Integer numeroFamigliari;

    @Column(name = "professione")
    private String professione;

    @Column(name = "fumatore")
    private Boolean fumatore;

    @Column(name = "posivito")
    private Boolean posivito;

    @Column(name = "isolamento_domiciliare")
    private Boolean isolamentoDomiciliare;

    @Column(name = "terapia_intensiva")
    private Boolean terapiaIntensiva;

    @Column(name = "guarito")
    private Boolean guarito;

    @Column(name = "deceduto")
    private Boolean deceduto;

    @Column(name = "data_tampone")
    private ZonedDateTime dataTampone;

    @Column(name = "data_guarito")
    private ZonedDateTime dataGuarito;

    @Column(name = "data_isolamento_domiciliare")
    private ZonedDateTime dataIsolamentoDomiciliare;

    @Column(name = "data_deceduto")
    private ZonedDateTime dataDeceduto;

    @Column(name = "data_terapia_intensiva")
    private ZonedDateTime dataTerapiaIntensiva;

    @Column(name = "data_dimissione")
    private ZonedDateTime dataDimissione;

    @Column(name = "giorni_isolamento_domiciliare")
    private Integer giorniIsolamentoDomiciliare;

    @Column(name = "giorni_terapia_intensiva")
    private Integer giorniTerapiaIntensiva;

    @Column(name = "giorni_guarito")
    private Integer giorniGuarito;

    @Column(name = "giorni_deceduto")
    private Integer giorniDeceduto;

    @Column(name = "nota")
    private String nota;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "paziente_sintomi",
               joinColumns = @JoinColumn(name = "paziente_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sintomi_id", referencedColumnName = "id"))
    private Set<Sintomo> sintomis = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "paziente_farmaci_usati",
               joinColumns = @JoinColumn(name = "paziente_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "farmaci_usati_id", referencedColumnName = "id"))
    private Set<Farmaco> farmaciUsatis = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "paziente_altre_patologie",
               joinColumns = @JoinColumn(name = "paziente_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "altre_patologie_id", referencedColumnName = "id"))
    private Set<Patologia> altrePatologies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Paziente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCf() {
        return cf;
    }

    public Paziente cf(String cf) {
        this.cf = cf;
        return this;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public Integer getAge() {
        return age;
    }

    public Paziente age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Sex getSex() {
        return sex;
    }

    public Paziente sex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getComune() {
        return comune;
    }

    public Paziente comune(String comune) {
        this.comune = comune;
        return this;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public Paziente provincia(String provincia) {
        this.provincia = provincia;
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRegione() {
        return regione;
    }

    public Paziente regione(String regione) {
        this.regione = regione;
        return this;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public Paziente latitudine(String latitudine) {
        this.latitudine = latitudine;
        return this;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public Paziente longitudine(String longitudine) {
        this.longitudine = longitudine;
        return this;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }

    public Integer getNumeroFamigliari() {
        return numeroFamigliari;
    }

    public Paziente numeroFamigliari(Integer numeroFamigliari) {
        this.numeroFamigliari = numeroFamigliari;
        return this;
    }

    public void setNumeroFamigliari(Integer numeroFamigliari) {
        this.numeroFamigliari = numeroFamigliari;
    }

    public String getProfessione() {
        return professione;
    }

    public Paziente professione(String professione) {
        this.professione = professione;
        return this;
    }

    public void setProfessione(String professione) {
        this.professione = professione;
    }

    public Boolean isFumatore() {
        return fumatore;
    }

    public Paziente fumatore(Boolean fumatore) {
        this.fumatore = fumatore;
        return this;
    }

    public void setFumatore(Boolean fumatore) {
        this.fumatore = fumatore;
    }

    public Boolean isPosivito() {
        return posivito;
    }

    public Paziente posivito(Boolean posivito) {
        this.posivito = posivito;
        return this;
    }

    public void setPosivito(Boolean posivito) {
        this.posivito = posivito;
    }

    public Boolean isIsolamentoDomiciliare() {
        return isolamentoDomiciliare;
    }

    public Paziente isolamentoDomiciliare(Boolean isolamentoDomiciliare) {
        this.isolamentoDomiciliare = isolamentoDomiciliare;
        return this;
    }

    public void setIsolamentoDomiciliare(Boolean isolamentoDomiciliare) {
        this.isolamentoDomiciliare = isolamentoDomiciliare;
    }

    public Boolean isTerapiaIntensiva() {
        return terapiaIntensiva;
    }

    public Paziente terapiaIntensiva(Boolean terapiaIntensiva) {
        this.terapiaIntensiva = terapiaIntensiva;
        return this;
    }

    public void setTerapiaIntensiva(Boolean terapiaIntensiva) {
        this.terapiaIntensiva = terapiaIntensiva;
    }

    public Boolean isGuarito() {
        return guarito;
    }

    public Paziente guarito(Boolean guarito) {
        this.guarito = guarito;
        return this;
    }

    public void setGuarito(Boolean guarito) {
        this.guarito = guarito;
    }

    public Boolean isDeceduto() {
        return deceduto;
    }

    public Paziente deceduto(Boolean deceduto) {
        this.deceduto = deceduto;
        return this;
    }

    public void setDeceduto(Boolean deceduto) {
        this.deceduto = deceduto;
    }

    public ZonedDateTime getDataTampone() {
        return dataTampone;
    }

    public Paziente dataTampone(ZonedDateTime dataTampone) {
        this.dataTampone = dataTampone;
        return this;
    }

    public void setDataTampone(ZonedDateTime dataTampone) {
        this.dataTampone = dataTampone;
    }

    public ZonedDateTime getDataGuarito() {
        return dataGuarito;
    }

    public Paziente dataGuarito(ZonedDateTime dataGuarito) {
        this.dataGuarito = dataGuarito;
        return this;
    }

    public void setDataGuarito(ZonedDateTime dataGuarito) {
        this.dataGuarito = dataGuarito;
    }

    public ZonedDateTime getDataIsolamentoDomiciliare() {
        return dataIsolamentoDomiciliare;
    }

    public Paziente dataIsolamentoDomiciliare(ZonedDateTime dataIsolamentoDomiciliare) {
        this.dataIsolamentoDomiciliare = dataIsolamentoDomiciliare;
        return this;
    }

    public void setDataIsolamentoDomiciliare(ZonedDateTime dataIsolamentoDomiciliare) {
        this.dataIsolamentoDomiciliare = dataIsolamentoDomiciliare;
    }

    public ZonedDateTime getDataDeceduto() {
        return dataDeceduto;
    }

    public Paziente dataDeceduto(ZonedDateTime dataDeceduto) {
        this.dataDeceduto = dataDeceduto;
        return this;
    }

    public void setDataDeceduto(ZonedDateTime dataDeceduto) {
        this.dataDeceduto = dataDeceduto;
    }

    public ZonedDateTime getDataTerapiaIntensiva() {
        return dataTerapiaIntensiva;
    }

    public Paziente dataTerapiaIntensiva(ZonedDateTime dataTerapiaIntensiva) {
        this.dataTerapiaIntensiva = dataTerapiaIntensiva;
        return this;
    }

    public void setDataTerapiaIntensiva(ZonedDateTime dataTerapiaIntensiva) {
        this.dataTerapiaIntensiva = dataTerapiaIntensiva;
    }

    public ZonedDateTime getDataDimissione() {
        return dataDimissione;
    }

    public Paziente dataDimissione(ZonedDateTime dataDimissione) {
        this.dataDimissione = dataDimissione;
        return this;
    }

    public void setDataDimissione(ZonedDateTime dataDimissione) {
        this.dataDimissione = dataDimissione;
    }

    public Integer getGiorniIsolamentoDomiciliare() {
        return giorniIsolamentoDomiciliare;
    }

    public Paziente giorniIsolamentoDomiciliare(Integer giorniIsolamentoDomiciliare) {
        this.giorniIsolamentoDomiciliare = giorniIsolamentoDomiciliare;
        return this;
    }

    public void setGiorniIsolamentoDomiciliare(Integer giorniIsolamentoDomiciliare) {
        this.giorniIsolamentoDomiciliare = giorniIsolamentoDomiciliare;
    }

    public Integer getGiorniTerapiaIntensiva() {
        return giorniTerapiaIntensiva;
    }

    public Paziente giorniTerapiaIntensiva(Integer giorniTerapiaIntensiva) {
        this.giorniTerapiaIntensiva = giorniTerapiaIntensiva;
        return this;
    }

    public void setGiorniTerapiaIntensiva(Integer giorniTerapiaIntensiva) {
        this.giorniTerapiaIntensiva = giorniTerapiaIntensiva;
    }

    public Integer getGiorniGuarito() {
        return giorniGuarito;
    }

    public Paziente giorniGuarito(Integer giorniGuarito) {
        this.giorniGuarito = giorniGuarito;
        return this;
    }

    public void setGiorniGuarito(Integer giorniGuarito) {
        this.giorniGuarito = giorniGuarito;
    }

    public Integer getGiorniDeceduto() {
        return giorniDeceduto;
    }

    public Paziente giorniDeceduto(Integer giorniDeceduto) {
        this.giorniDeceduto = giorniDeceduto;
        return this;
    }

    public void setGiorniDeceduto(Integer giorniDeceduto) {
        this.giorniDeceduto = giorniDeceduto;
    }

    public String getNota() {
        return nota;
    }

    public Paziente nota(String nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Set<Sintomo> getSintomis() {
        return sintomis;
    }

    public Paziente sintomis(Set<Sintomo> sintomos) {
        this.sintomis = sintomos;
        return this;
    }

    public Paziente addSintomi(Sintomo sintomo) {
        this.sintomis.add(sintomo);
        sintomo.getPazientis().add(this);
        return this;
    }

    public Paziente removeSintomi(Sintomo sintomo) {
        this.sintomis.remove(sintomo);
        sintomo.getPazientis().remove(this);
        return this;
    }

    public void setSintomis(Set<Sintomo> sintomos) {
        this.sintomis = sintomos;
    }

    public Set<Farmaco> getFarmaciUsatis() {
        return farmaciUsatis;
    }

    public Paziente farmaciUsatis(Set<Farmaco> farmacos) {
        this.farmaciUsatis = farmacos;
        return this;
    }

    public Paziente addFarmaciUsati(Farmaco farmaco) {
        this.farmaciUsatis.add(farmaco);
        farmaco.getPazientis().add(this);
        return this;
    }

    public Paziente removeFarmaciUsati(Farmaco farmaco) {
        this.farmaciUsatis.remove(farmaco);
        farmaco.getPazientis().remove(this);
        return this;
    }

    public void setFarmaciUsatis(Set<Farmaco> farmacos) {
        this.farmaciUsatis = farmacos;
    }

    public Set<Patologia> getAltrePatologies() {
        return altrePatologies;
    }

    public Paziente altrePatologies(Set<Patologia> patologias) {
        this.altrePatologies = patologias;
        return this;
    }

    public Paziente addAltrePatologie(Patologia patologia) {
        this.altrePatologies.add(patologia);
        patologia.getPazientis().add(this);
        return this;
    }

    public Paziente removeAltrePatologie(Patologia patologia) {
        this.altrePatologies.remove(patologia);
        patologia.getPazientis().remove(this);
        return this;
    }

    public void setAltrePatologies(Set<Patologia> patologias) {
        this.altrePatologies = patologias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paziente)) {
            return false;
        }
        return id != null && id.equals(((Paziente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Paziente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cf='" + getCf() + "'" +
            ", age=" + getAge() +
            ", sex='" + getSex() + "'" +
            ", comune='" + getComune() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", regione='" + getRegione() + "'" +
            ", latitudine='" + getLatitudine() + "'" +
            ", longitudine='" + getLongitudine() + "'" +
            ", numeroFamigliari=" + getNumeroFamigliari() +
            ", professione='" + getProfessione() + "'" +
            ", fumatore='" + isFumatore() + "'" +
            ", posivito='" + isPosivito() + "'" +
            ", isolamentoDomiciliare='" + isIsolamentoDomiciliare() + "'" +
            ", terapiaIntensiva='" + isTerapiaIntensiva() + "'" +
            ", guarito='" + isGuarito() + "'" +
            ", deceduto='" + isDeceduto() + "'" +
            ", dataTampone='" + getDataTampone() + "'" +
            ", dataGuarito='" + getDataGuarito() + "'" +
            ", dataIsolamentoDomiciliare='" + getDataIsolamentoDomiciliare() + "'" +
            ", dataDeceduto='" + getDataDeceduto() + "'" +
            ", dataTerapiaIntensiva='" + getDataTerapiaIntensiva() + "'" +
            ", dataDimissione='" + getDataDimissione() + "'" +
            ", giorniIsolamentoDomiciliare=" + getGiorniIsolamentoDomiciliare() +
            ", giorniTerapiaIntensiva=" + getGiorniTerapiaIntensiva() +
            ", giorniGuarito=" + getGiorniGuarito() +
            ", giorniDeceduto=" + getGiorniDeceduto() +
            ", nota='" + getNota() + "'" +
            "}";
    }
}
