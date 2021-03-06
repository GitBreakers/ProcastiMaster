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
import java.net.URL;

/**
 * This class describes an item that can be collected in a user's procrastination feed.
 * It can refer to external web pages, images or simply contain some text.
 *
 * @author Thomas Biesaart.
 */
@Entity
public class Distraction extends BaseEntity {
    private String text;
    private URL image;
    private URL link;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }
}
