package bootiful.kotlin


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

import org.springframework.data.annotation.Id
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.web.reactive.function.server.*


@SpringBootApplication
class KotlinApplication {


	@Bean
	fun http(customerRepository: CustomerRepository) = coRouter {
		GET("/customers") {
			ServerResponse.ok().bodyAndAwait(customerRepository.findAll())
		}

		GET("/customers/{id}") {
			val id = it.pathVariable("id").toInt()
			val result = customerRepository.findById(id)
			if (result != null)
			ServerResponse.ok().bodyValueAndAwait(result)
			else ServerResponse.notFound().buildAndAwait()
		}
	}



}


fun main(args: Array<String>) {
	runApplication<KotlinApplication>(*args)
}

interface CustomerRepository:CoroutineCrudRepository<Customer,Int>{

}

data class Customer(@Id val id: Int?, val name:String){

}
