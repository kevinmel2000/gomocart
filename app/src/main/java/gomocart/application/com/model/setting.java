package gomocart.application.com.model;

import java.io.Serializable;

public class setting implements Serializable {

    private static final long serialVersionUID = 1L;
    private String set_notifikasi;
    private boolean update_pesanan, notifikasi, informasi, chat;

    public setting(String set_notifikasi, boolean update_pesanan, boolean informasi, boolean notifikasi, boolean chat) {
        this.set_notifikasi  = set_notifikasi;
        this.update_pesanan = update_pesanan;
        this.notifikasi = notifikasi;
        this.informasi = informasi;
        this.chat = chat;
    }

    public String getSet_notifikasi() {
        return this.set_notifikasi;
    }

    public boolean getUpdate_pesanan() {
        return this.update_pesanan;
    }

    public boolean getNotifikasi() {
        return this.notifikasi;
    }

    public boolean getInformasi() {
        return this.informasi;
    }

    public boolean getChat() {
        return this.chat;
    }
}
