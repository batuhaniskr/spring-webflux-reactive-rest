# Spring Webflux Reactive Rest

Building Reactive Rest Service with Spring Flux.

This Project based on the Spring 5/Spring Boot 2.0 and functional programming model.

Uses these packages:

<ul>
<li>Spring Webflux</li>
<li>Reactive-Mongo</li>
<li>Spring 5</li> 
<li>Spring Boot 2.0</li>
</ul>

# Requirements

<ul>
<li>Java 1.8</li>
<li>MongoDB</li>
</ul>

Database name is hotels_db and collection name is hotels.

# Installation

### 1. Clone the application.

<pre>git clone https://github.com/batuhaniskr/spring-webflux-reactive-rest.git </pre>

### 2. Build and run the server application using maven

MongoDB needs to be run.

<pre>sudo mongod</pre> 

<pre>mvn clean install</pre>

<pre>mvn spring-boot:run</pre>

Application runs from http://localhost:8080/hotels

### Example Commands

#### Create Hotels
<pre> curl -X POST   http://localhost:8080/hotels  -H 'content-type: application/json' -d '{
    "name": "Konak Hotel",
    "address": "Çanakkale/Turkey",
    "createdAt": "2018-01-01"
}' </pre>

<pre> curl -X POST   http://localhost:8080/hotels  -H 'content-type: application/json' -d '{
    "name": "Balıklı Hotel",
    "address": "Balıkesir/Turkey",
    "createdAt": "2018-01-01"
}' </pre>


#### Get All Hotels

<pre>curl http://localhost:8080/hotels </pre>

#### Get a Hotel by id

<pre>curl http://localhost:8080/hotels/5a8d829cbb99221d55b5a221</pre>

#### Update a Hotel

<pre> curl -X PUT http://localhost:8080/hotels/5a8d83b9bb99221d55b5a222 -H 'content-type: application/json' -d '{
"name":"ErzurumHotel",
"address":"Erzurum/Turkey",
"createdAt":"2018-01-01"
}'
</pre>
