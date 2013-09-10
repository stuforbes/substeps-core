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


import com.technophobia.substeps.execution.node.StepNode;
import com.technophobia.substeps.model.Step;

import java.util.Collection;
import java.util.List;

/**
 * Interface used to find a list of steps with a given line
 */
public interface StepUsageAnalyser {

    /**
     * Find a list of step nodes using the step line
     *
     * @param line The step line, which may or may not be a pattern
     * @return All steps that have this line
     */
    Collection<StepNode> stepsWithLine(String line);
}
