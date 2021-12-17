package br.nom.carneiro.carlos.backend_challenge.domain.article;

public class Event {
    private String id;
    private String provider;

    public String getId() {
        return id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setId(String id) {
        this.id = id;
    }
}
