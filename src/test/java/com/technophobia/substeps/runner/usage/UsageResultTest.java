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
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class UsageResultTest {

    private UsageResult usageResult;

    @Before
    public void initialise(){
        this.usageResult = new UsageResult();
    }

    @Test
    public void addingAStepCreatesSingleItemCollectionForThatStepLine(){

        String stepLine = "a step";
        StepNode step = mock(StepNode.class);

        usageResult.registerStepFor(stepLine, step);

        Collection<StepNode> stepNodes = usageResult.stepUsages().get(stepLine);
        assertThat(stepNodes.size(), is(1));
    }

    @Test
    public void multipleStepsWithIdenticalStepLinesAreStoredTogether(){
        String stepLine = "a step";
        StepNode step1 = mock(StepNode.class);
        StepNode step2 = mock(StepNode.class);

        usageResult.registerStepFor(stepLine, step1);
        usageResult.registerStepFor(stepLine, step2);

        Collection<StepNode> stepNodes = usageResult.stepUsages().get(stepLine);
        assertThat(stepNodes.size(), is(2));
    }

    @Test
    public void clearingStepsRemovesAllData(){

        String stepLine = "a step";
        StepNode step = mock(StepNode.class);

        usageResult.registerStepFor(stepLine, step);

        assertThat(usageResult.stepUsages().get(stepLine).size(), is(1));

        usageResult.clearSteps();

        assertThat(usageResult.stepUsages().containsKey(stepLine), is(false));

    }
}
