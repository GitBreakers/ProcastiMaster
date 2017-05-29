/**
 * Copyright (C) 2017 Xillio GitBreakers (GitBreakers@xillio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.xillio.gitbreakers.procrastimaster.server.model.entity;

import javax.persistence.Entity;

/**
 * This class represents the post every working team member creates in the morning. It is simply an estimation of
 * what that member expects to do today.
 *
 * @author Thomas Biesaart
 */
@Entity
public class Planning extends BaseEntity {
    private String myFocus;
    private String todayIWill;
    private String needHelpWith;

    public String getMyFocus() {
        return myFocus;
    }

    public void setMyFocus(String myFocus) {
        this.myFocus = myFocus;
    }

    public String getTodayIWill() {
        return todayIWill;
    }

    public void setTodayIWill(String todayIWill) {
        this.todayIWill = todayIWill;
    }

    public String getNeedHelpWith() {
        return needHelpWith;
    }

    public void setNeedHelpWith(String needHelpWith) {
        this.needHelpWith = needHelpWith;
    }
}
