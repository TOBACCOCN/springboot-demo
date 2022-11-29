# springboot-example

### Build
1. Before running main class in idea, choose one env in pom.xml, 
the path is `View` --> `Tool Windows` --> `Maven`, then check one item in `Profiles`, 
after that, you must click `compile` which under `Lifecycle` in Maven view. This ensures that 
variable `profileActive` in pom.xml is available in application.properties or application.yml.
2. When using command line to build project, add parameter `-P` such as `mvn clean && mvn compile -Pdev`, 
`dev` can be replaced with `fat` or `pro` in this project.