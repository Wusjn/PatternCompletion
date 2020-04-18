# Pattern Completion

Given a pattern (list of APIs) and some code using it, this tool returns a code fragment according to responding pattern.

There are some holes in the code fragment, this tool also recommends some expression for each single hole.

## Usage

1. In order to parse client code which using target pattern(list of APIs), you should offer jar files that including those APIs. You should put those files in `/jar` directory, and modify `line 48 & 49` in `/src/main/java/extrctor/PatternInstanceExtractor` to refer to those jar files.
2. Put your patterns and source code files in `data` directory. Patten should be a list of APIs separated by whitespace. There are already some pattens and source code files in `data` directory. For example, `/data/fill_color/pattern` contains a pattern and `/data/fill_color/corpus` contains some source code files.
3. Modify the configurations, they are included in `src/main/java/utils/Config.java`:
   * `getAllPatternDir` : it returns the folder which contains all the pattern and codes, the default value is `/data` folder
   * `batchMode`: return true means that all patterns in `data` folder(specified by `getAllPatternDir`) will be processed, otherwise only pattern specified by `getFoucsPatternDir` will be processed
   * `getFoucsPatternDir` : when `batchMode` returns false, it specifies the pattern to be processed
   * `getSimilarThreshold` : there are some holes in the code fragment, this tool will cluster those holes to groups. Any two holes in a single group should be very similar, and the similarity is measured by the frequency that a same expression filled in those holes. So, this method specifies the threshold similarity that any two holes in a single group should satisfied. The default value is 0.95
   * `getReferenceThreshold` : similar to `getSimilarThreshold` . If variable A filled in hole A always depends to variable B filled in hole B, we say that hole A refers to hole B. This method specifies the threshold  frequency, and the default value is 0.95
4. then run `src/main/java/Main.java`
5. results will be in the directory which contains the patterns (file `recommendation` and`instances` ):
   * file `instances`  : contains all the instance we find in the source code files for that pattern
   * file `recommendation` : contains the code fragment and some type information. It's divided to three parts:
     * the code fragment
     * a list of holes in that code fragment. The type, a recommended expression and a frequently-used name for that hole is given.
     *  a list of FQNs. The FQNs of all elements -- types and methods -- in the code fragment are given here.