/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ynov.b3info.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ynov.b3info.models.PetType;
import com.ynov.b3info.services.ClinicService;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/pettypes")
public class PetTypeRestController {

	@Autowired
	private ClinicService clinicService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Collection<PetType>> getAllPetTypes(){
    	System.out.println(this.clinicService.toString());
		Collection<PetType> petTypes = new ArrayList<PetType>();
		petTypes.addAll(this.clinicService.findAllPetTypes());
		if (petTypes.isEmpty()){
			return new ResponseEntity<Collection<PetType>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<PetType>>(petTypes, HttpStatus.OK);
	}

	@RequestMapping(value = "/{petTypeId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<PetType> getPetType(@PathVariable("petTypeId") int petTypeId){
		PetType petType = this.clinicService.findPetTypeById(petTypeId);
		if(petType == null){
			return new ResponseEntity<PetType>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<PetType>(petType, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<PetType> addPetType(@RequestBody @Valid PetType petType, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (petType == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<PetType>(headers, HttpStatus.BAD_REQUEST);
		}
		this.clinicService.savePetType(petType);
		headers.setLocation(ucBuilder.path("/api/pettypes/{id}").buildAndExpand(petType.getId()).toUri());
		return new ResponseEntity<PetType>(petType, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{petTypeId}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<PetType> updatePetType(@PathVariable("petTypeId") int petTypeId, @RequestBody @Valid PetType petType, BindingResult bindingResult){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (petType == null)){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<PetType>(headers, HttpStatus.BAD_REQUEST);
		}
		PetType currentPetType = this.clinicService.findPetTypeById(petTypeId);
		if(currentPetType == null){
			return new ResponseEntity<PetType>(HttpStatus.NOT_FOUND);
		}
		currentPetType.setName(petType.getName());
		this.clinicService.savePetType(currentPetType);
		return new ResponseEntity<PetType>(currentPetType, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{petTypeId}", method = RequestMethod.DELETE, produces = "application/json")
	@Transactional
	public ResponseEntity<Void> deletePetType(@PathVariable("petTypeId") int petTypeId){
		PetType petType = this.clinicService.findPetTypeById(petTypeId);
		if(petType == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		this.clinicService.deletePetType(petType);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
