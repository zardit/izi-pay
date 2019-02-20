package fshn.edu.al;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.camel.BeanInject;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Component
public class BillingSystem {
	@PersistenceContext
	@Autowired
	public EntityManager entityManager;

	public BillResponse getBillBySerialNumber(String billSerialNumber, BillResponse authorization) {
		if (authorization.code.equals("1200")) {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Bill> q = cb.createQuery(Bill.class);
			Root<Bill> c = q.from(Bill.class);
			q.select(c).where(cb.equal(c.get("billSerialNumber"), billSerialNumber));
			int found = entityManager.createQuery(q).getResultList().size();
			if (found == 0) {
				BillResponse billResponse = new BillResponse(found, billSerialNumber);
				return billResponse;
			}

			else {
				Bill bill = entityManager.createQuery(q).getSingleResult();
				BillResponse billResponse = new BillResponse(bill, found);
				return billResponse;

			}
		}

		else {
			System.out.println("TEST = " + authorization);
			BillResponse billResponse = new BillResponse(0);
			billResponse.setBillResponseForNotAuthorizedRights();
			return billResponse;

		}

	}

	public BillResponse getBillsByContractNumber(String contractNumber, BillResponse authorization) {
		if (authorization.code.equals("1200")) {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Bill> q = cb.createQuery(Bill.class);
			Root<Bill> c = q.from(Bill.class);
			q.select(c).where(cb.equal(c.get("contractNumber"), contractNumber));

			List<Bill> bill = entityManager.createQuery(q).getResultList();
			int found = entityManager.createQuery(q).getResultList().size();
			if (found == 0) {
				BillResponse billResponse = new BillResponse(found, contractNumber, 'C');
				return billResponse;
			}

			else {
				List<Bill> bills = entityManager.createQuery(q).getResultList();
				BillResponse billResponse = new BillResponse(bills, found);
				return billResponse;

			}
		} else {
			BillResponse billResponse = new BillResponse(0);
			billResponse.setBillResponseForNotAuthorizedRights();
			return billResponse;
		}

	}
	@Transactional
	public BillResponse payBill(String billSerialNumber, String billContractNumber, String billAmount,
			String billInterest, String paymentReferenceNumber, BillResponse authorization) {
		if (authorization.code.equals("1200")) {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Bill> q = cb.createQuery(Bill.class);
			Root<Bill> c = q.from(Bill.class);
			CriteriaUpdate<Bill> q2 = cb.createCriteriaUpdate(Bill.class);

			q.select(c).where(cb.equal(c.get("billSerialNumber"), billSerialNumber));

			int found = entityManager.createQuery(q).getResultList().size();
			if (found == 0) {
				BillResponse billResponse = new BillResponse(found, billSerialNumber);
				return billResponse;
			}

			else {

				Bill bill = entityManager.createQuery(q).getSingleResult();
				found = 1;
				if (bill.getPaymentReferenceNumber() == null) {
					if (bill.getBillAmount() == Double.parseDouble(billAmount)
							&& bill.getInterest() == Double.parseDouble(billInterest)) {
						BillResponse billResponse = new BillResponse(found);
						CriteriaUpdate<Bill> update = cb.createCriteriaUpdate(Bill.class);
						Root e = update.from(Bill.class);
						update.set("status", "P");
						update.set("paymentReferenceNumber", paymentReferenceNumber);
				        update.where(cb.equal(c.get("billSerialNumber"), billSerialNumber));
						entityManager.createQuery(update).executeUpdate();

						return billResponse;
					} else {
						found = 0;
						BillResponse billResponse = new BillResponse(found);
						return billResponse;
					}

				}

				else {
					BillResponse billResponse = new BillResponse(0);
					billResponse.setBillResponseForExistingPaymentReference();
					return billResponse;
				}

			}
		} else {
			BillResponse billResponse = new BillResponse(0);
			billResponse.setBillResponseForNotAuthorizedRights();
			return billResponse;
		}

	}

	public BillResponse validateRequest(String authorization) throws Exception {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> q = cb.createQuery(User.class);
		Root<User> c = q.from(User.class);

		if (authorization.contains("null")) {
			BillResponse billResponse = new BillResponse(0);
			billResponse.setBillResponseForNotAuthorized();
			return billResponse;
		} else {

			byte[] byteArray = Base64.decodeBase64(authorization.getBytes());
			String decodedString = new String(byteArray);
			if (decodedString.indexOf(":") == -1) {
				BillResponse billResponse = new BillResponse(0);
				billResponse.setBillResponseForNotAuthorized();
				return billResponse;
			} else {
				String userName = decodedString.substring(0, decodedString.indexOf(":"));
				String password = decodedString.substring(decodedString.indexOf(":") + 1);
				System.out.println("STATUS A= " + password);
				System.out.println("PASSWORD  B= " + userName);
				q.select(c).where(cb.equal(c.get("userName"), userName));
				User user = entityManager.createQuery(q).getSingleResult();
				if (user.getStatus().equals("A") && user.getPassword().equals(password)
						&& user.getUserName().equals(userName)) {
					System.out.println("STATUS C= " + user.getStatus());
					System.out.println("PASSWORD D= " + user.getPassword());
					BillResponse billResponse = new BillResponse(1);
					billResponse.setBillResponseAuthorized();
					return billResponse;

				} else {
					System.out.println("STATUS E= " + user.getStatus());
					System.out.println("PASSWORD F= " + user.getPassword());
					System.out.println("PASSWORD H= " + user.getUserName());
					BillResponse billResponse = new BillResponse(0);
					billResponse.setBillResponseForNotAuthorizedRights();
					return billResponse;
				}
			}
		}
	}
}