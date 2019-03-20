package dataStructure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatternInstance implements Cloneable {
    private List<PatternInstanceLine> lines = new ArrayList<>();
    private String Signature = "";

    public void addLine(PatternInstanceLine line) {
        lines.add(line);
    }

    public boolean isIllegalPattern() {
        boolean isIllegal = false;
        for (PatternInstanceLine line : lines) {
            if (line.mode == PatternInstanceLine.Mode.ERROR) {
                isIllegal = true;
            }
        }
        return isIllegal;
    }


    public String getSignature() {
        if (Signature != "") {
            return Signature;
        }
        for (PatternInstanceLine line : lines) {
            Signature += line.getLineSignature() + '\n';
        }
        return Signature;
    }

    public List<String> pathSerialize() {
        List<String> fillers = new ArrayList<>();
        for (PatternInstanceLine line : lines) {
            fillers.addAll(line.pathSerialize());
        }
        return fillers;
    }

    public List<String> serialize() {
        List<String> fillers = new ArrayList<>();
        for (PatternInstanceLine line : lines) {
            fillers.addAll(line.serialize());
        }
        return fillers;
    }

    public void deserialize(List<String> fillers) {
        int startPoint = 0;
        for (PatternInstanceLine line : lines) {
            startPoint = line.deserialize(fillers, startPoint);
        }
    }

    public List<String> typeSerialize() {
        List<String> types = new ArrayList<>();
        for (PatternInstanceLine line : lines) {
            types.addAll(line.typeSerialize());
        }
        return types;
    }

    public List<String> getSymbols() {
        List<String> symbols = new ArrayList<>();
        for (PatternInstanceLine line : lines) {
            symbols.addAll(line.getSymbols());
        }
        Set<String> reducedSymbols = new HashSet<>(symbols);
        return new ArrayList<>(reducedSymbols);
    }

    public List<Integer> getIntermediateHoles() {
        int startPos = 0;
        List<Integer> intermediateHoles = new ArrayList<>();
        for (PatternInstanceLine line : lines) {
            if (line.mode == PatternInstanceLine.Mode.ASSIGN) {
                intermediateHoles.add(startPos);
            }
            startPos += line.serialize().size();
        }
        return intermediateHoles;
    }

    public String toString() {
        String pattern = "";
        for (PatternInstanceLine line : lines) {
            pattern += line.toString() + "\n";
        }
        return pattern;
    }

    @Override
    public Object clone() {
        PatternInstance clone = null;
        try {
            clone = (PatternInstance) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        clone.lines = new ArrayList<>();
        for (PatternInstanceLine line : lines) {
            clone.lines.add((PatternInstanceLine) line.clone());
        }

        return clone;
    }
}
