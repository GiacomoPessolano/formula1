/*
 * MIT License
 *
 * Copyright (c) 2024 Giacomo Pessolano
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package it.unicam.cs.giacomopessolano.formula1.grid;

import java.io.IOException;

/**
 * Interface that parses a player's info from .txt files with a specific format.
 */
public interface GridInitializerFromTxt {

    /**
     * Parses grid from a .txt file.
     *
     * @param filename Name of the .txt file.
     * @throws IOException If there is an error while reading the file or its format is incorrect.
     */
    void initialize(String filename) throws IOException;

    /**
     * Returns the gird if initialized.
     *
     * @return Grid, or null if not initialized.
     */
    Grid getGrid();

    /**
     * Checks whether the given file is a .txt file.
     *
     * @param filename File to check
     * @throws IOException If the file doesn't use .txt extension.
     */
    default void validateFileExtension(String filename) throws IOException {
        if (!filename.endsWith(".txt")) {
            throw new IOException("File must have a .txt extension.");
        }
    }
}