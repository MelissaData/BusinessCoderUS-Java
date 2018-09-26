@echo off
javac -classpath ".\melissadata\businesscoder\org.apache.sling.commons.json-2.0.20.jar;" .\melissadata\businesscoder\*.java .\melissadata\businesscoder\view\*.java ./melissadata\businesscoder\model\*.java
java -classpath ".\melissadata\businesscoder\org.apache.sling.commons.json-2.0.20.jar;"; melissadata.businesscoder.Main melissadata.businesscoder.view.BusinessCoderController melissadata.businesscoder.view.BusinessCoderTransactionController melissadata.businesscoder.view.RootLayoutController melissadata.businesscoder.model.BusinessCoderOption
del .\melissadata\businesscoder\*.class 
del .\melissadata\businesscoder\view\*.class 
del .\melissadata\businesscoder\model\*.class