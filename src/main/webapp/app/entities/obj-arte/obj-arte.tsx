import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './obj-arte.reducer';
import { IObjArte } from 'app/shared/model/obj-arte.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IObjArteProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ObjArte = (props: IObjArteProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { objArteList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="obj-arte-heading" data-cy="ObjArteHeading">
        <Translate contentKey="museoApp.objArte.home.title">Obj Artes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="museoApp.objArte.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="museoApp.objArte.home.createLabel">Create new Obj Arte</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {objArteList && objArteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="museoApp.objArte.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('idObjArt')}>
                  <Translate contentKey="museoApp.objArte.idObjArt">Id Obj Art</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="museoApp.objArte.exhibicion">Exhibicion</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('paisCultura')}>
                  <Translate contentKey="museoApp.objArte.paisCultura">Pais Cultura</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('anio')}>
                  <Translate contentKey="museoApp.objArte.anio">Anio</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tituloObj')}>
                  <Translate contentKey="museoApp.objArte.tituloObj">Titulo Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('descObj')}>
                  <Translate contentKey="museoApp.objArte.descObj">Desc Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('epocaObj')}>
                  <Translate contentKey="museoApp.objArte.epocaObj">Epoca Obj</Translate> <FontAwesomeIcon icon="sort" />
                </th>

                <th />
              </tr>
            </thead>
            <tbody>
              {objArteList.map((objArte, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${objArte.id}`} color="link" size="sm">
                      {objArte.id}
                    </Button>
                  </td>
                  <td>{objArte.idObjArt}</td>
                  <td>{objArte.exhibicion ? <Link to={`exhibicion/${objArte.exhibicion.id}`}>{objArte.exhibicion.nomExi}</Link> : ''}</td>
                  <td>{objArte.paisCultura}</td>
                  <td>{objArte.anio}</td>
                  <td>{objArte.tituloObj}</td>
                  <td>{objArte.descObj}</td>
                  <td>{objArte.epocaObj}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${objArte.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${objArte.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${objArte.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="museoApp.objArte.home.notFound">No Obj Artes found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={objArteList && objArteList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ objArte }: IRootState) => ({
  objArteList: objArte.entities,
  loading: objArte.loading,
  totalItems: objArte.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ObjArte);
