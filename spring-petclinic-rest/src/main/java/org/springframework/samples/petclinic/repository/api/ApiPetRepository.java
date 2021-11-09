package org.springframework.samples.petclinic.repository.api;

import java.util.Collection;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(path = "/api/", name = "PETCLINIC-PETS")
public interface ApiPetRepository extends PetRepository {

	@RequestMapping(value = "/pets/pettypes", method = RequestMethod.GET, produces = "application/json")
    List<PetType> findPetTypes() throws DataAccessException;

	@RequestMapping(value = "/pets/{petId}", method = RequestMethod.GET, produces = "application/json")
    Pet findPetById(@PathVariable("petId") int id) throws DataAccessException;

	@RequestMapping(value = "/pets", method = RequestMethod.POST, produces = "application/json")
    void savePet(@RequestBody Pet pet) throws DataAccessException;
    
	@RequestMapping(value = "/pets", method = RequestMethod.GET, produces = "application/json")
	Collection<Pet> findAllPets() throws DataAccessException;

	@RequestMapping(value = "/pets/{petId}", method = RequestMethod.DELETE, produces = "application/json")
	void deletePet(@PathVariable("petId") int petId) throws DataAccessException;
	
	@RequestMapping(value = "/owners/*/lastname/{lastName}", method = RequestMethod.GET, produces = "application/json")
	Collection<Owner> findOwnerByLastName(@PathVariable("lastName") String lastName);
	
	@RequestMapping(value = "/owners/{ownerId}", method = RequestMethod.GET, produces = "application/json")
	Owner findOwnerById(@PathVariable("ownerId") int id);
	
	@RequestMapping(value = "/owners", method = RequestMethod.POST, produces = "application/json")
	void saveOwner(@RequestBody Owner owner);
	
	@RequestMapping(value = "/owners", method = RequestMethod.GET, produces = "application/json")
	Collection<Owner> findAllOwners();
	
	@RequestMapping(value = "/owners/{ownerId}", method = RequestMethod.DELETE, produces = "application/json")
	void deleteOwner(@PathVariable("ownerId") int ownerId);
	
	@RequestMapping(value = "/owners/*/petname/{petName}", method = RequestMethod.GET, produces = "application/json")
	Collection<Owner> findByPets_Name(@PathVariable("petName") String petName);
	
	@Override
	@RequestMapping(value = "/pettypes/{petTypeId}", method = RequestMethod.GET, produces = "application/json")
	public PetType findTypeById(@PathVariable("petTypeId") int id) throws DataAccessException;

	@Override
	@RequestMapping(value = "/pettypes", method = RequestMethod.GET, produces = "application/json")
	public Collection<PetType> findAllTypes() throws DataAccessException;

	@Override
	@RequestMapping(value = "/pettypes", method = RequestMethod.POST, produces = "application/json")
	public void saveType(@RequestBody PetType petType) throws DataAccessException;

	@Override
	@RequestMapping(value = "/pettypes/{petTypeId}", method = RequestMethod.DELETE, produces = "application/json")
	public void deleteType(@PathVariable("petTypeId") int petTypeId) throws DataAccessException;
}
