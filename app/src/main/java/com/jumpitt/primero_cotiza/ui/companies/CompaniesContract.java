package com.jumpitt.primero_cotiza.ui.companies;

import com.jumpitt.primero_cotiza.model.Company;

import java.util.List;

public interface CompaniesContract {

    interface View {

        void setCompanies(List<Company> companyList);

        void companiesFailed(String message);

        void enableResult(int result);

        void disableResult(int result);
    }

    interface Presenter {

        void getCompanies();

        void companyEnabled(int companyId);

        void companyDisabled(int companyId);
    }

    interface Interactor {

        void getCompanies();

        void companyEnabled(int companyId);

        void companyDisabled(int companyId);

        void finishSesion();
    }

    interface InteractorOutput {

        void setCompanies(List<Company> companyList);

        void companiesFailed(String message);

        void enableResult(int result);

        void disableResult(int result);

        void refreshLogin();
    }

    interface Router {

        void toLogin();
    }
}
