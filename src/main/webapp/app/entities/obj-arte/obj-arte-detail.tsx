import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './obj-arte.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IObjArteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ObjArteDetail = (props: IObjArteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { objArteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="objArteDetailsHeading">
          <Translate contentKey="museoApp.objArte.detail.title">ObjArte</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{objArteEntity.id}</dd>
          <dt>
            <span id="idObjArt">
              <Translate contentKey="museoApp.objArte.idObjArt">Id Obj Art</Translate>
            </span>
          </dt>
          <dd>{objArteEntity.idObjArt}</dd>
          <dt>
            <span id="paisCultura">
              <Translate contentKey="museoApp.objArte.paisCultura">Pais Cultura</Translate>
            </span>
          </dt>
          <dd>{objArteEntity.paisCultura}</dd>
          <dt>
            <span id="anio">
              <Translate contentKey="museoApp.objArte.anio">Anio</Translate>
            </span>
          </dt>
          <dd>{objArteEntity.anio}</dd>
          <dt>
            <span id="tituloObj">
              <Translate contentKey="museoApp.objArte.tituloObj">Titulo Obj</Translate>
            </span>
          </dt>
          <dd>{objArteEntity.tituloObj}</dd>
          <dt>
            <span id="descObj">
              <Translate contentKey="museoApp.objArte.descObj">Desc Obj</Translate>
            </span>
          </dt>
          <dd>{objArteEntity.descObj}</dd>
          <dt>
            <span id="epocaObj">
              <Translate contentKey="museoApp.objArte.epocaObj">Epoca Obj</Translate>
            </span>
          </dt>
          <dd>{objArteEntity.epocaObj}</dd>
          <dt>
            <Translate contentKey="museoApp.objArte.artista">Artista</Translate>
          </dt>
          <dd>
            {objArteEntity.artistas
              ? objArteEntity.artistas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.nomArt}</a>
                    {objArteEntity.artistas && i === objArteEntity.artistas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="museoApp.objArte.exhibicion">Exhibicion</Translate>
          </dt>
          <dd>{objArteEntity.exhibicion ? objArteEntity.exhibicion.nomExi : ''}</dd>
        </dl>
        <Button tag={Link} to="/obj-arte" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/obj-arte/${objArteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ objArte }: IRootState) => ({
  objArteEntity: objArte.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ObjArteDetail);
