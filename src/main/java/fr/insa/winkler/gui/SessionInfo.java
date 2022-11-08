/*
    Copyright 2000- Francois de Bertrand de Beuvron

    This file is part of CoursBeuvron.

    CoursBeuvron is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    CoursBeuvron is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.winkler.gui;

import fr.insa.winkler.projet.Utilisateur;
import java.sql.Connection;
import java.util.Optional;

/**
 *
 * @author marie et théo
 */
public class SessionInfo {

    private Optional<Utilisateur> curUser;
    private Connection conBdD;

    public SessionInfo() {
        this.curUser = Optional.empty();
        this.conBdD = null;
    }

    public boolean userConnected() {
        return this.curUser.isPresent();
    }

    public Optional<Utilisateur> getCurUser() {
        return this.curUser;
    }

    public void setCurUser(Optional<Utilisateur> curUser) {
        this.curUser = curUser;
    }

    public int getUserID() {
        return this.curUser.orElseThrow().getId();
    }

    /**
     * @return the conBdD
     */
    public Connection getConBdD() {
        return conBdD;
    }

    /**
     * @param conBdD the conBdD to set
     */
    public void setConBdD(Connection conBdD) {
        this.conBdD = conBdD;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return this.curUser.orElseThrow().getNom();
    }

}
