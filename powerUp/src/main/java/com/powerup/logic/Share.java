package com.powerup.logic;

/**
 * A company share.
 * @author Oliver Lewisohn
 * @since 2014-01-22
 */
public class Share {

    private final Company company;

    /**
     * Creates a new share.
     * @param company The company that owns the share.
     */
    public Share(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }
}
