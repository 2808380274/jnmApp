package com.example.administrator.anew.DataBean;

/**
 * Created by Administrator on 2018/10/17.
 */

public class UserBean {


    /**
     * code : 200
     * msg : 成功!
     * data : {"key":"00d91e8e0cca2b76f515926a36db68f5","phone":"13594347817","name":"peakchao","passwd":"123456","text":"这是我的签名。","img":"https://res.apiopen.top/2018031817571995.key.png","other":"这是我的备注信息1","other2":"这是我的备注信息2","createTime":"2018-03-18 17:57:20"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * key : 00d91e8e0cca2b76f515926a36db68f5
         * phone : 13594347817
         * name : peakchao
         * passwd : 123456
         * text : 这是我的签名。
         * img : https://res.apiopen.top/2018031817571995.key.png
         * other : 这是我的备注信息1
         * other2 : 这是我的备注信息2
         * createTime : 2018-03-18 17:57:20
         */

        private String key;
        private String phone;
        private String name;
        private String passwd;
        private String text;
        private String img;
        private String other;
        private String other2;
        private String createTime;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public String getOther2() {
            return other2;
        }

        public void setOther2(String other2) {
            this.other2 = other2;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
