package org.example.sii_task.repositories;

import org.example.sii_task.models.fundraiser.Fundraiser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FundraiserRepository extends CrudRepository<Fundraiser, Long> {
    Optional<Fundraiser> getFundraiserByName(String name);

}
