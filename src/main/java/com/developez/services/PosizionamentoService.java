package com.developez.services;

import com.developez.model.*;
import com.developez.repository.CardRepository;
import com.developez.repository.PosizionamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PosizionamentoService {

    private final PosizionamentoRepository posizionamentoRepository;
    private final SchieramentoService schieramentoService;

    private final CardRepository cardRepository;

    @Autowired
    public PosizionamentoService( PosizionamentoRepository posizionamentoRepository,
                                  SchieramentoService schieramentoService, CardRepository cardRepository ) {
        this.posizionamentoRepository = posizionamentoRepository;
        this.schieramentoService = schieramentoService;
        this.cardRepository = cardRepository;
    }

    public Posizionamento findById( Integer id ) {
        return posizionamentoRepository.findById( id ).orElse( null );
    }

    public Posizionamento save( Integer cardId,
                                Integer schieramentoId,
                                String role,
                                String side ) {
        Card card = cardRepository.findById( cardId ).orElse( null );

        if( card != null ) {
            Schieramento schieramento = schieramentoService.findById( schieramentoId );
            if( schieramento != null ) {
                Posizionamento posizionamento = Posizionamento
                        .builder()
                        .role( Role.valueOf( role.toUpperCase() ) )
                        .side( Side.valueOf( side.toUpperCase() ))
                        .card( card )
                        .schieramento( schieramento )
                        .build();
                return posizionamentoRepository.save( posizionamento );
            }
        }
        return null;
    }

    public Posizionamento modify(Integer posizionamentoId,
                                 String role,
                                 String side) {
        Posizionamento posizionamento = posizionamentoRepository.findById( posizionamentoId ).orElse( null );
        if( posizionamento != null ) {
            posizionamento.setRole( Role.valueOf( role.toUpperCase() ) );
            posizionamento.setSide( Side.valueOf( side.toUpperCase() ) );
            return posizionamentoRepository.save( posizionamento );
        }
        return null;
    }
}
