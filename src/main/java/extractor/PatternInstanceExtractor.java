package extractor;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import dataStructure.PatternInstance;
import dataStructure.PatternInstanceLine;
import utils.Config;
import utils.DirExplorer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PatternInstanceExtractor {
    //Singleton
    private static PatternInstanceExtractor instance;

    private PatternInstanceExtractor() {
    }

    public static PatternInstanceExtractor getInstance() {
        if (instance == null) {
            instance = new PatternInstanceExtractor();
        }
        return instance;
    }

    //Data
    private ArrayList<String> patternApis;
    private List<PatternInstance> patternInstances;
    private DirExplorer corpusDirExplorer;

    //Initialization
    private void initializeSymbolSolver() {
        try {
            TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
            TypeSolver jarTypeSolver = new JarTypeSolver("poi/poi-3.17.jar");
            TypeSolver jarTypeSolver2 = new JarTypeSolver("poi/poi-ooxml-3.17.jar");
            TypeSolver combinedTypeSolver = new CombinedTypeSolver(reflectionTypeSolver, jarTypeSolver, jarTypeSolver2);

            JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
            JavaParser.getStaticConfiguration().setSymbolResolver(symbolSolver);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void loadPatternApis(String patternFilePath) {
        patternApis = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(patternFilePath));
            String patternAndFrequency = reader.readLine();
            String pattern = patternAndFrequency.substring(0, patternAndFrequency.lastIndexOf(" :"));
            for (String methodCall : pattern.split(" ")) {
                patternApis.add(methodCall);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void initializeExtractor(String patternFilePath) {
        initializeSymbolSolver();

        loadPatternApis(patternFilePath);
        patternInstances = new ArrayList<>();
        corpusDirExplorer = new DirExplorer(
                (level, path, file) -> path.endsWith("java"),
                new extractorVisitor()
        );
    }

    //The only exposed method
    public List<PatternInstance> extract(String patternFilePath, String corpusDirPath) {
        initializeExtractor(patternFilePath);

        System.out.println("------------------------------------");
        System.out.println("Start Extraction Process");
        System.out.println("------------------------------------");
        corpusDirExplorer.explore(new File(corpusDirPath));
        System.out.println("------------------------------------");
        System.out.println("Extraction Process Done");
        System.out.println("------------------------------------");

        return patternInstances;
    }




    private class extractorVisitor implements DirExplorer.FileHandler {
        private int currentFocusApi;
        private PatternInstance patternInstance;

        @Override
        public void handle(int level, String path, File file) {
            try {
                System.out.println("Parsing File : " + file.getName());
                CompilationUnit cu = JavaParser.parse(file);
                cu.accept(
                        new VoidVisitorAdapter<Object>() {
                            public void visit(MethodDeclaration n, Object arg) {
                                currentFocusApi = 0;
                                patternInstance = new PatternInstance();
                                super.visit(n, arg);
                            }

                            public void visit(MethodCallExpr n, Object arg) {
                                super.visit(n, arg);
                                if (!n.getName().toString().equals(instance.patternApis.get(currentFocusApi))) {
                                    return;
                                }
                                currentFocusApi++;
                                patternInstance.addLine(new PatternInstanceLine(n));
                                if (currentFocusApi == instance.patternApis.size()) {
                                    currentFocusApi = 0;
                                    instance.patternInstances.add(patternInstance);
                                    patternInstance = new PatternInstance();
                                }
                            }
                        }
                        , null);
            } catch (Exception e) {
                if(Config.debug()){
                    e.printStackTrace();
                }
            }
        }
    }
}
