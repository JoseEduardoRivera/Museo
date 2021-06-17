import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './artista.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IArtistaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArtistaDetail = (props: IArtistaDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { artistaEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artistaDetailsHeading">
          <Translate contentKey="museoApp.artista.detail.title">Artista</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{artistaEntity.id}</dd>
          <dt>
            <span id="nomArt">
              <Translate contentKey="museoApp.artista.nomArt">Nom Art</Translate>
            </span>
          </dt>
          <dd>{artistaEntity.nomArt}</dd>
          <dt>
            <span id="fechNac">
              <Translate contentKey="museoApp.artista.fechNac">Fech Nac</Translate>
            </span>
          </dt>
          <dd>{artistaEntity.fechNac}</dd>
          <dt>
            <span id="fechDefu">
              <Translate contentKey="museoApp.artista.fechDefu">Fech Defu</Translate>
            </span>
          </dt>
          <dd>{artistaEntity.fechDefu}</dd>
          <dt>
            <span id="paisOrigen">
              <Translate contentKey="museoApp.artista.paisOrigen">Pais Origen</Translate>
            </span>
          </dt>
          <dd>{artistaEntity.paisOrigen}</dd>
          <dt>
            <span id="estiloArt">
              <Translate contentKey="museoApp.artista.estiloArt">Estilo Art</Translate>
            </span>
          </dt>
          <dd>{artistaEntity.estiloArt}</dd>
          <dt>
            <span id="epoca">
              <Translate contentKey="museoApp.artista.epoca">Epoca</Translate>
            </span>
          </dt>
          <dd>{artistaEntity.epoca}</dd>
        </dl>
        <Button tag={Link} to="/artista" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artista/${artistaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ artista }: IRootState) => ({
  artistaEntity: artista.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArtistaDetail);
