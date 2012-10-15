/**
 * Copyright (C) 2012 (nick @ objectdefinitions.com)
 *
 * This file is part of Object Definitions od-swing.
 *
 * od-swing is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * od-swing is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with od-swing.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.od.swing.action;

import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nick Ebbutt
 * Date: 24/02/12
 * Time: 18:14
 */
public class CompositeActionModel extends AbstractActionModel {

    private List<ActionModel> delegateModels;

    public CompositeActionModel(ActionModel... models) {
        this(Arrays.asList(models));
    }

    public CompositeActionModel(List<ActionModel> delegateModels) {
        this.delegateModels = delegateModels;
        addDelegateListener();
        checkValidity();
    }

    private void addDelegateListener() {
        ActionModelListener delegateListener = new ActionModelListener() {
            public void actionStateUpdated() {
                checkValidity();
            }
        };

        for ( ActionModel m : delegateModels) {
            m.addActionModelListener(delegateListener);
        }
    }

    private void checkValidity() {
        //model is valid only if all delegate models are valid
        boolean result = true;
        for ( ActionModel m : delegateModels) {
            if ( ! m.isModelValid()) {
                result = false;
                break;
            }
        }

        boolean changed = setModelValid(result);
        if ( changed ) {
            fireActionStateUpdated();
        }
    }

    protected void doClearActionModelState() {
        for ( ActionModel m : delegateModels) {
            m.clearActionModelState();
        }
    }
}
