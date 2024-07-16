import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './interest.reducer';

export const Interest = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const interestList = useAppSelector(state => state.interest.entities);
  const loading = useAppSelector(state => state.interest.loading);
  const links = useAppSelector(state => state.interest.links);
  const updateSuccess = useAppSelector(state => state.interest.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="interest-heading" data-cy="InterestHeading">
        <Translate contentKey="leadManagementApp.interest.home.title">Interests</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="leadManagementApp.interest.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/interest/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="leadManagementApp.interest.home.createLabel">Create new Interest</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={interestList ? interestList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {interestList && interestList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="leadManagementApp.interest.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('category')}>
                    <Translate contentKey="leadManagementApp.interest.category">Category</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('category')} />
                  </th>
                  <th className="hand" onClick={sort('brand')}>
                    <Translate contentKey="leadManagementApp.interest.brand">Brand</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('brand')} />
                  </th>
                  <th className="hand" onClick={sort('articleId')}>
                    <Translate contentKey="leadManagementApp.interest.articleId">Article Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('articleId')} />
                  </th>
                  <th className="hand" onClick={sort('orderId')}>
                    <Translate contentKey="leadManagementApp.interest.orderId">Order Id</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('orderId')} />
                  </th>
                  <th className="hand" onClick={sort('isPurchased')}>
                    <Translate contentKey="leadManagementApp.interest.isPurchased">Is Purchased</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('isPurchased')} />
                  </th>
                  <th className="hand" onClick={sort('createdAt')}>
                    <Translate contentKey="leadManagementApp.interest.createdAt">Created At</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdAt')} />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="leadManagementApp.interest.createdBy">Created By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('createdBy')} />
                  </th>
                  <th className="hand" onClick={sort('updatedAt')}>
                    <Translate contentKey="leadManagementApp.interest.updatedAt">Updated At</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedAt')} />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="leadManagementApp.interest.updatedBy">Updated By</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('updatedBy')} />
                  </th>
                  <th>
                    <Translate contentKey="leadManagementApp.interest.lead">Lead</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {interestList.map((interest, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/interest/${interest.id}`} color="link" size="sm">
                        {interest.id}
                      </Button>
                    </td>
                    <td>{interest.category}</td>
                    <td>{interest.brand}</td>
                    <td>{interest.articleId}</td>
                    <td>{interest.orderId}</td>
                    <td>{interest.isPurchased ? 'true' : 'false'}</td>
                    <td>{interest.createdAt ? <TextFormat type="date" value={interest.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{interest.createdBy}</td>
                    <td>{interest.updatedAt ? <TextFormat type="date" value={interest.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{interest.updatedBy}</td>
                    <td>{interest.lead ? <Link to={`/lead/${interest.lead.id}`}>{interest.lead.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/interest/${interest.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/interest/${interest.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/interest/${interest.id}/delete`)}
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
                <Translate contentKey="leadManagementApp.interest.home.notFound">No Interests found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Interest;
