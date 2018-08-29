/*
 *    Copyright 2018 Fabien Boismoreau
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.bwaim.scorekeeper;

/**
 * Created by Fabien Boismoreau on 29/08/2018.
 * <p>
 */
public class ConfigurationModel {

    // Players informations
    private String nameA;
    private String nameB;
    private int initialScore;

    // Game informations
    private int syntaxError;
    private int logicError;
    private int virusAttack;
    private int initialTime;

    public String getNameA() {
        return nameA;
    }

    public void setNameA(String nameA) {
        this.nameA = nameA;
    }

    public String getNameB() {
        return nameB;
    }

    public void setNameB(String nameB) {
        this.nameB = nameB;
    }

    public int getInitialScore() {
        return initialScore;
    }

    public void setInitialScore(int initialScore) {
        this.initialScore = initialScore;
    }

    public int getSyntaxError() {
        return syntaxError;
    }

    public void setSyntaxError(int syntaxError) {
        this.syntaxError = syntaxError;
    }

    public int getLogicError() {
        return logicError;
    }

    public void setLogicError(int logicError) {
        this.logicError = logicError;
    }

    public int getVirusAttack() {
        return virusAttack;
    }

    public void setVirusAttack(int virusAttack) {
        this.virusAttack = virusAttack;
    }

    public int getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(int initialTime) {
        this.initialTime = initialTime;
    }
}