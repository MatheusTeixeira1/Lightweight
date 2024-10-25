package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class HelloBean {
    private String nome;
    private String mensagem;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public String digaOla() {
        mensagem = "Olá, " + nome + "!";
        return null;
    }
}
