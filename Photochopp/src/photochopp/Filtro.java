/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photochopp;

/**
 *
 * @author Moicho
 */
public class Filtro {

    protected String filter = null;

    public void setFilter(String filter) {
        if (filter == null) {
            throw new IllegalArgumentException("Filtro inválido.");
        } else {
            this.filter = filter;
        }
    }

    public String getFilter() {
        return filter;
    }

}
