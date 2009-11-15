/**
 * Copyright (C) 2009 (nick @ objectdefinitions.com)
 *
 * This file is part of JTimeseries.
 *
 * JTimeseries is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTimeseries is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTimeseries.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.od.swing.action;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nick Ebbutt
 * Date: 29-May-2009
 * Time: 10:31:46
 */
public class ListSelectionActionModel<E> extends AbstractActionModel {

    private List<E> selectedSeries = new LinkedList<E>();

    public void setSelected(E... series) {
        setSelected(Arrays.asList(series));
    }

    public void setSelected(List<E> series) {
        selectedSeries.clear();
        selectedSeries.addAll(series);
        setValidity();
    }

    public void addSelected(E series) {
        selectedSeries.add(series);
        setValidity();
    }

    public void removeSelected(E series) {
        selectedSeries.remove(series);
        setValidity();
    }

    public void setSelected(E series){
        selectedSeries.clear();
        selectedSeries.add(series);
        setValidity();
    }

    public List<E> getSelected() {
        return Collections.unmodifiableList(selectedSeries);
    }

    private void setValidity() {
        setModelValid(selectedSeries.size() > 0);
    }

    protected void doClearActionModelState() {
        selectedSeries.clear();
    }
}
