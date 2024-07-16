import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Interest from './interest';
import InterestDetail from './interest-detail';
import InterestUpdate from './interest-update';
import InterestDeleteDialog from './interest-delete-dialog';

const InterestRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Interest />} />
    <Route path="new" element={<InterestUpdate />} />
    <Route path=":id">
      <Route index element={<InterestDetail />} />
      <Route path="edit" element={<InterestUpdate />} />
      <Route path="delete" element={<InterestDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InterestRoutes;
