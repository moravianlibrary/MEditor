/*
 * Metadata Editor
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.fi.muni.xkremser.editor.client.view.other;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.tile.TileGrid;

import cz.fi.muni.xkremser.editor.client.util.Constants;
import cz.fi.muni.xkremser.editor.client.view.window.ModalWindow;

/**
 * @author Jiri Kremser
 * @version 6.1.2012
 */
public final class PageNumberingManager {

    private final TileGrid tileGrid;

    public PageNumberingManager(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
    }

    /** The attribute stringNumber */
    private static final String ATTR_STRING_NUMBER = "stringNumber";

    private void shiftToLeft(final int moveOver,
                             final Record[] allRecords,
                             final Record[] selectedRecords,
                             final int selLength,
                             final int lastNum) {
        Map<Integer, Record> bufferedRecords = new HashMap<Integer, Record>();
        int lastIndexOfSel = selLength - 1;
        int lastIndex = tileGrid.getRecordList().indexOf(selectedRecords[selLength - 1]);
        int lastNumber = lastNum;
        if (lastNum == Integer.MIN_VALUE) {
            lastNumber = allRecords.length;
            for (int i = 0; i <= lastIndex; i++) {
                String startingNumber = allRecords[i].getAttributeAsString(Constants.ATTR_NAME);
                int stringNumber = getPageNumberFromText(startingNumber);
                if (stringNumber == Integer.MIN_VALUE) {
                    stringNumber = ++lastNumber;
                } else {
                    lastNumber = stringNumber;
                }
                allRecords[i].setAttribute(ATTR_STRING_NUMBER, stringNumber);
            }
        }

        int lastIndexOfAll = tileGrid.getRecordList().indexOf(selectedRecords[selLength - 1]);

        for (int i = lastNumber; (i > 0 && (lastIndexOfSel >= 0)) || !(bufferedRecords.isEmpty()); i--) {

            if (lastIndexOfAll > -1 && allRecords[lastIndexOfAll].getAttributeAsInt(ATTR_STRING_NUMBER) == i) {
                if (!(lastIndexOfSel < 0)
                        && allRecords[lastIndexOfAll].equals(selectedRecords[lastIndexOfSel])) {

                    int newPosition = 0;
                    if (i - (lastIndexOfSel - moveOver) <= 0) {
                        newPosition = lastIndexOfSel + 1;
                    } else {
                        newPosition = i + moveOver;
                    }
                    renumber(newPosition, allRecords[lastIndexOfAll], false);

                    bufferedRecords.put(newPosition, allRecords[lastIndexOfAll]);
                    allRecords[lastIndexOfAll] = null;
                    lastIndexOfSel--;
                }

                if (lastIndexOfAll != lastIndex && (i > -1)) {
                    if (bufferedRecords.containsKey(i + (lastIndex - lastIndexOfAll))) {
                        while (bufferedRecords.containsKey(i + (lastIndex - lastIndexOfAll))) {
                            allRecords[lastIndex] = bufferedRecords.remove(i + (lastIndex - lastIndexOfAll));
                            lastIndex--;
                        }

                    } else if (allRecords[lastIndexOfAll] != null && lastIndexOfAll > -1) {

                        renumber(bufferedRecords.size(), allRecords[lastIndexOfAll], true);

                        allRecords[lastIndex] = allRecords[lastIndexOfAll];
                        lastIndex--;
                    }
                } else if (lastIndexOfAll == lastIndex && allRecords[lastIndexOfAll] != null) {
                    lastIndex--;
                }
                lastIndexOfAll--;
            }
            while (bufferedRecords.containsKey(i + (lastIndex - lastIndexOfAll))) {
                allRecords[lastIndex] = bufferedRecords.remove(i + (lastIndex - lastIndexOfAll));
                lastIndex--;
            }
        }
    }

    private void shiftToRight(int moveOver,
                              Record[] allRecords,
                              Record[] selectedRecords,
                              int selLength,
                              int firstNum,
                              int maxNum) {

        Map<Integer, Record> bufferedRecords = new HashMap<Integer, Record>();
        int firstIndex = tileGrid.getRecordList().indexOf(selectedRecords[0]);
        int lastIndexOfSel = 0;
        int lastIndexOfAll = firstIndex;
        int allLength = allRecords.length;
        int maxNumber = maxNum;
        int firstNumber = firstNum;
        if (firstNum == Integer.MIN_VALUE || maxNum == Integer.MIN_VALUE) {
            firstNumber = allLength;
            maxNumber = allLength;
            for (int i = allLength - 1; i >= firstIndex; i--) {
                String startingNumber = allRecords[i].getAttributeAsString(Constants.ATTR_NAME);
                int stringNumber = getPageNumberFromText(startingNumber);
                if (stringNumber == Integer.MIN_VALUE) {
                    stringNumber = --firstNumber;
                } else {
                    firstNumber = stringNumber;
                }
                if (maxNumber < firstNumber) maxNumber = stringNumber;
                allRecords[i].setAttribute(ATTR_STRING_NUMBER, stringNumber);
            }
        }
        for (int i = firstNumber; (i <= maxNumber && (lastIndexOfSel < selLength))
                || !(bufferedRecords.isEmpty()); i++) {

            if (lastIndexOfAll < allLength
                    && allRecords[lastIndexOfAll].getAttributeAsInt(ATTR_STRING_NUMBER) == i) {
                if (lastIndexOfSel < selLength
                        && allRecords[lastIndexOfAll].equals(selectedRecords[lastIndexOfSel])) {

                    int newPosition = 0;
                    newPosition = i + moveOver;
                    renumber(newPosition, allRecords[lastIndexOfAll], false);
                    bufferedRecords.put(newPosition, allRecords[lastIndexOfAll]);
                    allRecords[lastIndexOfAll] = null;
                    lastIndexOfSel++;
                }

                if (lastIndexOfAll != firstIndex && (i <= maxNumber)) {
                    if (bufferedRecords.containsKey(i + (firstIndex - lastIndexOfAll))) {
                        while (bufferedRecords.containsKey(i + (firstIndex - lastIndexOfAll))) {
                            allRecords[firstIndex] =
                                    bufferedRecords.remove(i + (firstIndex - lastIndexOfAll));
                            firstIndex++;
                        }

                    } else if (allRecords[lastIndexOfAll] != null && lastIndexOfAll < allLength) {

                        renumber(-bufferedRecords.size(), allRecords[lastIndexOfAll], true);

                        allRecords[firstIndex] = allRecords[lastIndexOfAll];
                        firstIndex++;
                    }
                } else if (lastIndexOfAll == firstIndex && allRecords[lastIndexOfAll] != null) {
                    firstIndex++;
                }
                lastIndexOfAll++;
            }
            while (bufferedRecords.containsKey(i + (firstIndex - lastIndexOfAll))) {
                allRecords[firstIndex] = bufferedRecords.remove(i + (firstIndex - lastIndexOfAll));
                firstIndex++;
            }
        }
    }

    public void shift(final int n, final boolean isShiftTo, final Canvas canvas) {
        final ModalWindow mw = new ModalWindow(canvas);
        mw.setLoadingIcon("loadingAnimation.gif");
        mw.show(true);

        Timer timer = new Timer() {

            @Override
            public void run() {
                Record[] selectedRecords = tileGrid.getSelection();
                tileGrid.selectAllRecords();
                Record[] allRecords = tileGrid.getSelection();
                int selLength = selectedRecords.length;
                int maxNumber = Integer.MIN_VALUE;
                int firstNumber = Integer.MIN_VALUE;
                int lastNumber = Integer.MIN_VALUE;

                if (selectedRecords != null && selectedRecords.length > 0) {
                    int moveOver = n;

                    if (isShiftTo) {
                        maxNumber = allRecords.length;
                        firstNumber = 0;
                        lastNumber = maxNumber;
                        int number = 0;

                        for (int i = 0; i < allRecords.length; i++) {
                            String startingNumber = allRecords[i].getAttributeAsString(Constants.ATTR_NAME);
                            int stringNumber = getPageNumberFromText(startingNumber);
                            if (stringNumber == Integer.MIN_VALUE) {
                                stringNumber = ++number;
                            } else {
                                number = stringNumber;
                            }
                            if (allRecords[i].equals(selectedRecords[0])) firstNumber = stringNumber;
                            if (allRecords[i].equals(selectedRecords[selLength - 1]))
                                lastNumber = stringNumber;
                            if (maxNumber < firstNumber) maxNumber = firstNumber;
                            allRecords[i].setAttribute(ATTR_STRING_NUMBER, stringNumber);
                        }
                        if (n != firstNumber) {
                            moveOver = n - firstNumber;
                        } else {
                            moveOver = 0;
                        }
                    }

                    if (moveOver == 0) {
                        tileGrid.selectRecords(selectedRecords);
                        mw.hide();
                        return;
                    }

                    if (moveOver < 0) {
                        shiftToLeft(moveOver, allRecords, selectedRecords, selLength, lastNumber);
                    } else {
                        shiftToRight(moveOver, allRecords, selectedRecords, selLength, firstNumber, maxNumber);
                    }
                    tileGrid.removeSelectedData();
                    tileGrid.setData(allRecords);
                    tileGrid.selectRecords(selectedRecords);
                }
            }
        };
        timer.schedule(25);
        mw.hide();
    }

    private void renumber(int newNumber, Record record, boolean fromString) {
        String stringNumber = record.getAttributeAsString(Constants.ATTR_NAME);
        int number = getPageNumberFromText(stringNumber);

        if (number != Integer.MIN_VALUE) {
            StringBuffer sb = new StringBuffer("");
            if (stringNumber.contains("[")) sb.append("[");
            sb.append(fromString ? (number + newNumber) : newNumber);
            if (stringNumber.contains("]")) sb.append("]");
            record.setAttribute(Constants.ATTR_NAME, sb.toString());
        }
    }

    public void toAbcN(final int n) {
        if (n > 26 || n < 2)
            throw new IllegalArgumentException("bad argument (allowed values are between 2 and 26)");
        final char[] alphabet =
                {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                        's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        Record[] data = tileGrid.getSelection();
        if (data != null && data.length > 0) {
            String startingNumber = data[0].getAttributeAsString(Constants.ATTR_NAME);
            int i = getPageNumberFromText(startingNumber);
            if (i == Integer.MIN_VALUE) {
                i = tileGrid.getRecordIndex(data[0]) + 1;
            }
            int j = 0;
            for (Record rec : data) {
                rec.setAttribute(Constants.ATTR_NAME, (i + (j / n)) + "" + alphabet[j % n]);
                j++;
            }
        }
    }

    public void foliation() {
        Record[] data = tileGrid.getSelection();
        if (data != null && data.length > 0) {
            String startingNumber = data[0].getAttributeAsString(Constants.ATTR_NAME);
            int i = getPageNumberFromText(startingNumber);
            if (i == Integer.MIN_VALUE) {
                i = tileGrid.getRecordIndex(data[0]) + 1;
            }
            int j = 0;
            for (Record rec : data) {
                rec.setAttribute(Constants.ATTR_NAME, (i + (j / 2)) + "" + (j % 2 == 0 ? 'r' : 'v'));
                j++;
            }
        }
    }

    public void toColumn() {
        List<Record> selection = Arrays.asList(tileGrid.getSelection());
        RecordList allRecords = tileGrid.getRecordList();
        String name = selection.get(0).getAttributeAsString(Constants.ATTR_NAME);
        int number = getPageNumberFromText(name);
        if (number < 0) {
            if (name.contains(Constants.TWO_PAGES_SEPARATOR)) {
                String[] splitedName = name.split("\\" + Constants.TWO_PAGES_SEPARATOR);
                if ((number = getPageNumberFromText(splitedName[0].replaceAll(" ", ""))) < 0)
                    number = allRecords.indexOf(selection.get(0)) + 1;
            } else {
                number = allRecords.indexOf(selection.get(0)) + 1;
            }
        }
        number--;

        for (int i = allRecords.indexOf(selection.get(0)); i < allRecords.getLength(); i++) {
            if (selection.contains(allRecords.get(i))
                    || allRecords.get(i).getAttributeAsString(Constants.ATTR_NAME)
                            .contains(Constants.TWO_PAGES_SEPARATOR)) {
                allRecords.get(i).setAttribute(Constants.ATTR_NAME,
                                               ++number + Constants.TWO_PAGES_SEPARATOR + ++number);
            } else {
                allRecords.get(i).setAttribute(Constants.ATTR_NAME, ++number);
            }
        }
    }

    private int getPageNumberFromText(String text) {
        if (text != null && !"".equals(text)) {
            try {
                if (text.charAt(0) == '[') {
                    text = text.substring(1, text.lastIndexOf(']'));
                }
                while (!"".equals(text) && !Character.isDigit(text.charAt(text.length() - 1))) {
                    text = text.substring(0, text.length() - 1);
                }
                if (!"".equals(text)) {
                    return Integer.parseInt(text);
                }
            } catch (NumberFormatException nfe) {
                return Integer.MIN_VALUE;
            }
        }
        return Integer.MIN_VALUE;
    }

}
