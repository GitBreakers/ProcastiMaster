package nl.xillio.gitbreakers.procrastimaster.client.services;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by SanderVisser on 29-5-2017.
 */
public class TableEntry {
        private final SimpleStringProperty user;
        private final SimpleStringProperty info;

        public TableEntry(String user, String info) {
            this.user = new SimpleStringProperty(user);
            this.info = new SimpleStringProperty(info);
        }

        public String getUser() {
            return user.get();
        }
        public void setUser(String user) {
            this.user.set(user);
        }

        public String getInfo() {
            return info.get();
        }
        public void setInfo(String info) {
            this.info.set(info);
        }


    }

