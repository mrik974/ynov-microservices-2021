package org.springframework.samples.petclinic.repository.api;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(path = "/api/", name = "PETCLINIC-VETS")
public interface ApiVetRepository extends VetRepository {

	@RequestMapping(value = "/vets", method = RequestMethod.GET, produces = "application/json")
	Collection<Vet> findAllVets() throws DataAccessException;

	@RequestMapping(value = "/vets/{vetId}", method = RequestMethod.GET, produces = "application/json")
	Vet findVetById(@PathVariable("vetId") int id) throws DataAccessException;

	@RequestMapping(value = "/vets", method = RequestMethod.POST, produces = "application/json")
	void saveVet(@RequestBody Vet vet) throws DataAccessException;

	@RequestMapping(value = "/vets/{vetId}", method = RequestMethod.DELETE, produces = "application/json")
	void deleteVet(@PathVariable("vetId") int vetId) throws DataAccessException;
	
	@RequestMapping(value = "/vets/by-specialty/{specialty}", method = RequestMethod.GET, produces = "application/json")
	Collection<Vet> findVetsBySpecialty(@PathVariable("specialty") String specialty);
	
	@RequestMapping(value = "/specialties/{specialtyId}", method = RequestMethod.GET, produces = "application/json")
	public Specialty findSpecialtyById(@PathVariable("specialtyId") int id) throws DataAccessException;

	@RequestMapping(value = "/specialties", method = RequestMethod.GET, produces = "application/json")
	public Collection<Specialty> findAllSpecialties() throws DataAccessException;

	@RequestMapping(value = "/specialties", method = RequestMethod.POST, produces = "application/json")
	public void saveSpecialty(@RequestBody Specialty specialty) throws DataAccessException;

	@RequestMapping(value = "/specialties/{specialtyId}", method = RequestMethod.DELETE, produces = "application/json")
	public void deleteSpecialty(@PathVariable("specialtyId") Integer specialtyId) throws DataAccessException;

}
