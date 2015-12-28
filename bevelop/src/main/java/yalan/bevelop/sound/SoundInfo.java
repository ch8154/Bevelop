/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalan.bevelop.sound;

import android.net.Uri;

/**
 *
 * @author user
 */
public class SoundInfo {

    protected String messageId;
    protected String path;
    protected Integer proccess = 0;
    protected Integer max = 1;
    protected Uri uri;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getProccess() {
        return proccess;
    }

    public void setProccess(Integer proccess) {
        this.proccess = proccess;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean isFile() {
        return (path == null || !path.equals(""));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SoundInfo other = (SoundInfo) obj;
        if ((this.messageId == null) ? (other.messageId != null) : !this.messageId.equals(other.messageId)) {
            return false;
        }
        if ((this.path == null) ? (other.path != null) : !this.path.equals(other.path)) {
            return false;
        }
        return true;
    }

}
