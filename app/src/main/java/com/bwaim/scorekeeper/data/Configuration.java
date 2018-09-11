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

package com.bwaim.scorekeeper.data;

/**
 * Created by Fabien Boismoreau on 29/08/2018.
 * <p>
 */
public class Configuration {

    // Players information
    private String nameA;
    private String nameB;
    private int initialScore;
    private int initialScoreA;
    private int initialScoreB;

    // Game information
    private int syntaxError;
    private int logicError;
    private int virusAttack;
    private long initialTime;
    private long time;

    public Configuration() {
        nameA = "Toto";
        nameB = "Coder B";
        initialScore = 25;
        initialScoreA = initialScore;
        initialScoreB = initialScore;
        syntaxError = 1;
        logicError = 3;
        virusAttack = 5;
        initialTime = 90000;
        time = initialTime;
    }

    public Configuration(String nameA, String nameB, int initialScore, int syntaxError
            , int logicError, int virusAttack, int initialTime) {
        this.nameA = nameA;
        this.nameB = nameB;
        this.initialScore = initialScore;
        this.initialScoreA = initialScore;
        this.initialScoreB = initialScore;
        this.syntaxError = syntaxError;
        this.logicError = logicError;
        this.virusAttack = virusAttack;
        this.time = initialTime;
        this.initialTime = initialTime;
    }

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

    public long getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(long initialTime) {
        this.initialTime = initialTime;
    }

    public int getInitialScoreA() {
        return initialScoreA;
    }

    public void setInitialScoreA(int initialScoreA) {
        this.initialScoreA = initialScoreA;
    }

    public int getInitialScoreB() {
        return initialScoreB;
    }

    public void setInitialScoreB(int initialScoreB) {
        this.initialScoreB = initialScoreB;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
