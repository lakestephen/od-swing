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
