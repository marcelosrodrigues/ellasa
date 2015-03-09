package com.pmrodrigues.ellasa.models;

import com.pmrodrigues.ellasa.Constante;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class Imagem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private String url;

    public Imagem(String arquivo) {
        this();
        this.url = Constante.URL_IMAGENS + arquivo;
    }

    public Imagem() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
