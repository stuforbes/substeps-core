/*
 *	Copyright Technophobia Ltd 2012
 *
 *   This file is part of Substeps.
 *
 *    Substeps is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    Substeps is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with Substeps.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.technophobia.substeps.runner.usage;

import com.google.common.collect.Maps;
import com.technophobia.substeps.execution.node.StepNode;

import java.util.*;

public class UsageResult {

    private final Map<String, Set<StepNode>> steps;

    public UsageResult(){
        this.steps = Maps.newHashMap();
    }

    public void registerStepFor(String stepLine, StepNode stepNode) {
        if(!steps.containsKey(stepLine)){
            steps.put(stepLine, new HashSet<StepNode>());
        }
        steps.get(stepLine).add(stepNode);
    }

    public Map<String, Set<StepNode>> stepUsages(){
        return Collections.unmodifiableMap(steps);
    }

    public void clearSteps() {
        steps.clear();
    }
}
