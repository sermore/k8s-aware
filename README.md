# A spring-boot application kubernetes-aware

A spring-boot exercise to identify the minimal set of features needed to run an application inside a kubernetes cluster.
* health indicators: liveness and readiness
* application.yaml is loaded from ConfigMap, spring-cloud features configmap monitoring and application reload on change

### Prerequisites
* java jdk-11
* git
* docker

### Prepare environment
* [install kubectl](https://kubernetes.io/docs/tasks/tools/#kubectl)
* [install minikube](https://minikube.sigs.k8s.io/docs/start/)
* start a shell and run `minikube start`
* run `eval $(minikube docker-env)`
  with this command you instruct the docker command to use the docker daemon inside minikube cluster, in this way the docker images are build directly inside the minikube's docker instance, and there is no need to push them. All docker related operations need to be executed in this shell, [more details here](https://minikube.sigs.k8s.io/docs/handbook/pushing/#1-pushing-directly-to-the-in-cluster-docker-daemon-docker-env).
### Create, deploy and run the application with correct, internal, state
* create docker image `./mvnw spring-boot:build-image`; An image named `net.reliqs/k8s-aware:0.0.1-SNAPSHOT` should be visibile in the images list `docker images`
* build kubernetes configuration `./mvnw k8s:resource`
  the `kubernetes.yaml` is created in `target/classes/META-INF/jkube`
* apply configuration to kubernetes cluster `./mvnw k8s:apply`
* the application should now be running, access the log with `kubectl logs -lapp=k8s-aware`
* access the application using `minikube service k8s-aware`
* check that everything is ok querying container's details `kubectl describe pod -lapp=k8s-aware`
### Apply the faulty configuration to simulate malfunctions  
* create the configmap named k8b-aware `kubectl create configmap k8s-aware -o yaml --from-file=src/test/resources/faulty-application.yaml`
* observe how the application reloads with the new behaviour `kubectl logs -lapp=k8s-aware`
* observe details of the container `kubectl get pod -lapp=k8s-aware -w`  `kubectl describe pod -lapp=k8s-aware`
### Clean up 
* dispose deployments `./mvnw k8s:undeploy`
* delete configmap `kubectl delete configmap k8s-aware`
* stop minikube `minikube stop`


### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.5/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.4.5/reference/htmlsingle/#production-ready)
* [Deploying Spring Boot Application on Kubernetes using Kubernetes Maven Plugin](https://faun.pub/deploying-spring-boot-application-on-kubernetes-using-kubernetes-maven-plugin-46caf22b03a5)
* [Spring on Kubernetes!](https://hackmd.io/@ryanjbaxter/spring-on-k8s-workshop#Spring-on-Kubernetes)
* [Eclipse JKube???](https://www.eclipse.org/jkube/)
* [springboot-example of using spring cloud config client with kubernetes(k8s) configmap](https://www.bswen.com/2020/11/springboot-springcloud-kubernetes-k8s-configmap-example.html)
* [spring-cloud-kubernetes/spring-cloud-kubernetes-examples](https://github.com/spring-cloud/spring-cloud-kubernetes/tree/master/spring-cloud-kubernetes-examples)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
